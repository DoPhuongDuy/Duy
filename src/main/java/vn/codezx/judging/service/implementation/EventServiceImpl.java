package vn.codezx.judging.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.codezx.judging.Exception.AlreadyExistsException;
import vn.codezx.judging.Exception.DataNotFoundException;
import vn.codezx.judging.Exception.DataValidationException;
import vn.codezx.judging.dtos.EventDTO;
import vn.codezx.judging.entity.Event;
import vn.codezx.judging.entity.PackagePlan;
import vn.codezx.judging.entity.User;
import vn.codezx.judging.entity.UserPackageMapping;
import vn.codezx.judging.mapper.EventMapper;
import vn.codezx.judging.repository.EventRepository;
import vn.codezx.judging.repository.PackagePlanRepository;
import vn.codezx.judging.repository.UserPackageMappingRepository;
import vn.codezx.judging.repository.UserRepository;
import vn.codezx.judging.service.EventService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final PackagePlanRepository packagePlanRepository;
    private final UserPackageMappingRepository userPackageMappingRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public Event createEvent(EventDTO eventDTO) {

        if (ChronoUnit.YEARS.between(LocalDate.now(), eventDTO.getStartDate()) > 1) {
            throw new DataValidationException("Khong duoc dien ra sau 1 nam");
        }
        User existingUser = findUserWithId(eventDTO.getOrganizerId());
        if(checkTimeEventsByOrganizerId(eventDTO, existingUser) != null) {
            throw new DataValidationException("An event already exists and is going.");
        }
        UserPackageMapping existingUserPackageMapping = userPackageMappingRepository.findByUserIdAndPackagePlanId(eventDTO.getOrganizerId(), eventDTO.getPlanid()).orElseThrow(()-> new DataNotFoundException("Cannot find UserPackageMapping"));

        PackagePlan existingPackagePlan = packagePlanRepository.findById(eventDTO.getPlanid()).orElseThrow(()-> new DataNotFoundException("Cannot find PackagePlan"));
        if(ChronoUnit.MONTHS.between(eventDTO.getStartDate(), eventDTO.getEndDate()) > existingPackagePlan.getMaxEventDuration()){
            throw new DataValidationException("Khong duoc dien qua 6 thang");
        }
        if(existingUserPackageMapping.getNumberEvent()== 0){
            throw new DataValidationException("Bạn đã đến giới hạn của Package vui lòng chọn package khác hoạc mua thêm");
        }
        existingUserPackageMapping.setNumberEvent(existingUserPackageMapping.getNumberEvent() - 1);

        userPackageMappingRepository.save(existingUserPackageMapping);

        Event newEvent = eventMapper.toEntity(eventDTO, existingUser, existingPackagePlan);
        return eventRepository.save(newEvent);
    }

    @Override
    public List<Event> getAllEventByOrganizerId(long OrganizerId) {
        User existingUser = findUserWithId(OrganizerId);
        return eventRepository.findByOrganizerId(existingUser);
    }

    @Override
    public Event getEventById(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Event Not Found with id "+id));
    }

    @Override
    public Event updateEventById(long id, EventDTO eventDTO) {
        Event existingEvent = getEventById(id);

        PackagePlan existingPackagePlan = packagePlanRepository.findById(existingEvent.getPackagePlan().getId()).orElseThrow(()-> new DataNotFoundException("Cannot find PackagePlan"));
        if(ChronoUnit.MONTHS.between(eventDTO.getStartDate(), eventDTO.getEndDate()) > existingPackagePlan.getMaxEventDuration()){
            throw new DataValidationException("Khong duoc dien qua 6 thang");
        }
        if (ChronoUnit.YEARS.between(existingEvent.getCreated_at().toLocalDate(), eventDTO.getStartDate()) > 1) {
            throw new DataValidationException("Khong duoc dien ra sau 1 nam");
        }
        //Update
        User existingUser = findUserWithId(eventDTO.getOrganizerId());
        if(checkTimeEventsByOrganizerId(eventDTO, existingUser) != null) {
            throw new AlreadyExistsException("An event already exists and is going.");
        }
        existingEvent.setEventName(eventDTO.getEventName());
        existingEvent.setDescription(eventDTO.getDescription());
        existingEvent.setSchoolId(eventDTO.getSchoolId());

        return eventRepository.save(existingEvent);
    }

    @Override
    public void deleteEventById(long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        optionalEvent.ifPresent(eventRepository::delete);
    }

    @Override
    public boolean existsByEventName(String eventName) {
        return eventRepository.existsByEventName(eventName);
    }


    //Tìm user theo id
    public User findUserWithId(long id){
        return  userRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("User Not Found with id "+id));
    }

    //Hạn chế Query quá nhiều
    public List<Event> checkTimeEventsByOrganizerId(EventDTO eventDTO, User OrganizerId) {

        List<Event> events = eventRepository.findByOrganizerId(OrganizerId);

        if (!events.isEmpty()) {
            for (int i = events.size() - 1; i >= 0; i--) {
                Event event = events.get(i);
                if (event.getEventName().equals(eventDTO.getEventName())) {
                    if(event.getEndDate().isAfter(eventDTO.getStartDate())){
                        return List.of(event); // Trả về event đầu tiên thỏa mãn từ cuối lên
                    }
                    return null;
                }else{
                    if (ChronoUnit.YEARS.between(event.getCreated_at().toLocalDate(), eventDTO.getStartDate()) > 2) {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}
