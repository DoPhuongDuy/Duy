package vn.codezx.judging.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.codezx.judging.Exception.DataNotFoundException;
import vn.codezx.judging.Exception.DataValidationException;
import vn.codezx.judging.dtos.CriteriaPackDTO;
import vn.codezx.judging.entity.Criteria;
import vn.codezx.judging.entity.CriteriaPack;
import vn.codezx.judging.entity.Event;
import vn.codezx.judging.repository.CriteriaPackRepository;
import vn.codezx.judging.repository.CriteriaRepository;
import vn.codezx.judging.repository.EventRepository;
import vn.codezx.judging.service.CriteriaPackService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CriteriaPackServiceImpl implements CriteriaPackService {

    private final EventRepository eventRepository;
    private final CriteriaPackRepository criteriaPackRepository;
    private final CriteriaRepository criteriaRepository;

    // Tạo mới CriteriaPack từ CriteriaPackDTO
    public CriteriaPack createCriteriaPack(CriteriaPackDTO criteriaPackDTO) {
        if(criteriaPackRepository.existsByPackName(criteriaPackDTO.getPackName())){
            throw new DataValidationException("CriteriaPack is exist");
        }
        Event existingEven = eventRepository.findById(criteriaPackDTO.getEventId()).orElseThrow(()-> new DataNotFoundException("Cannot find Event"));
        CriteriaPack criteriaPack = CriteriaPack.builder()
                .packName(criteriaPackDTO.getPackName())
                .description(criteriaPackDTO.getDescription())
                .roundNumber(criteriaPackDTO.getRoundNumber())
                .event(existingEven)
                .build();

        Criteria existingCriteria = criteriaRepository.findById(criteriaPackDTO.getCriteriaId()).orElseThrow(()-> new DataValidationException("Cannot find Criteria"));

        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(existingCriteria);
        criteriaPack.setCriteriaList(criteriaList);

        return criteriaPackRepository.save(criteriaPack);
}

    @Override
    public List<CriteriaPack> getAllCriteriaPackByEventId(long eventId) {
        return criteriaPackRepository.findByEventId(eventId);
    }

    @Override
    public CriteriaPack getCriteriaPackById(long id) {
        return criteriaPackRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Cannot find CriteriaPack"));
    }

    @Override
    public CriteriaPack updateCriteriaPackById(long id, CriteriaPackDTO criteriaPackDTO) {
        CriteriaPack criteriaPack = criteriaPackRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("CriteriaPack not found with id: " + id));
        criteriaPack.setPackName(criteriaPackDTO.getPackName());
        if(criteriaPackDTO.getPackName() != criteriaPack.getPackName()){
            if(criteriaPackRepository.existsByPackName(criteriaPackDTO.getPackName())){
                throw new DataValidationException("CriteriaPack is exist");
            }
        }
        if(criteriaPackDTO.getEventId() != criteriaPack.getEvent().getId()){
            Event existingEvent = eventRepository.findById(criteriaPackDTO.getEventId()).orElseThrow(()-> new DataNotFoundException("Cannot find Event"));
            criteriaPack.setEvent(existingEvent);
        }
        criteriaPack.setDescription(criteriaPackDTO.getDescription());
        criteriaPack.setRoundNumber(criteriaPackDTO.getRoundNumber());

        // Truy xuất danh sách Criteria hiện tại từ criteriaPack
        List<Criteria> currentCriteriaList = criteriaPack.getCriteriaList();

        // Nếu danh sách Criteria hiện tại rỗng, khởi tạo mới
        if (currentCriteriaList == null) {
            currentCriteriaList = new ArrayList<>();
        }

        // Duyệt qua các ID từ DTO
        boolean exists = currentCriteriaList.stream()
                .anyMatch(criteria -> criteria.getId() == criteriaPackDTO.getCriteriaId());

        if (!exists) {
            Criteria newCriteria = criteriaRepository.findById(criteriaPackDTO.getCriteriaId())
                    .orElseThrow(() -> new DataNotFoundException("Criteria not found with ID: " + criteriaPackDTO.getCriteriaId()));
            currentCriteriaList.add(newCriteria);
        }

        return criteriaPackRepository.save(criteriaPack);
    }

    @Override
    public void deleteCriteriaPackMapingById(long id, CriteriaPackDTO criteriaPackDTO) {
        CriteriaPack existingCriteriaPack = criteriaPackRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find CriteriaPack"));

        // Truy xuất danh sách Criteria hiện tại từ criteriaPack
        List<Criteria> currentCriteriaList = existingCriteriaPack.getCriteriaList();

        // Nếu danh sách Criteria hiện tại rỗng, không cần làm gì
        if (currentCriteriaList == null || currentCriteriaList.isEmpty()) {
            throw new DataNotFoundException("No Criteria found in the pack to delete.");
        }

        // Tìm Criteria cần xóa từ danh sách hiện tại
        Criteria criteriaToRemove = currentCriteriaList.stream()
                .filter(criteria -> criteria.getId() == criteriaPackDTO.getCriteriaId())
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("Criteria not found in the current pack with ID: " + criteriaPackDTO.getCriteriaId()));

        // Xóa Criteria ra khỏi danh sách
        currentCriteriaList.remove(criteriaToRemove);

        // Cập nhật lại danh sách vào CriteriaPack
        existingCriteriaPack.setCriteriaList(currentCriteriaList);

        // Lưu lại vào database
        criteriaPackRepository.save(existingCriteriaPack);
    }


    @Override
    public void deleteCriteriaPackById(long id) {
        CriteriaPack criteriaPack = criteriaPackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CriteriaPack not found with id: " + id));
        criteriaPackRepository.delete(criteriaPack);
    }

    @Override
    public boolean existsByPackName(String packName) {
        return criteriaPackRepository.existsByPackName(packName);
    }
}
