package vn.codezx.judging.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.codezx.judging.Exception.DataNotFoundException;
import vn.codezx.judging.Exception.DataValidationException;
import vn.codezx.judging.dtos.UserPackageMappingDTO;
import vn.codezx.judging.entity.PackagePlan;
import vn.codezx.judging.entity.User;
import vn.codezx.judging.entity.UserPackageMapping;
import vn.codezx.judging.mapper.UserPackageMappingMapper;
import vn.codezx.judging.repository.PackagePlanRepository;
import vn.codezx.judging.repository.UserPackageMappingRepository;
import vn.codezx.judging.repository.UserRepository;
import vn.codezx.judging.service.UserPackageMappingSerrvice;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPackageMappingSerrviceImpl implements UserPackageMappingSerrvice {

    private final UserPackageMappingRepository userPackageMappingRepository;
    private final UserRepository userRepository;
    private final PackagePlanRepository packagePlanRepository;
    private final UserPackageMappingMapper userPackageMappingMapper;

    @Override
    public List<UserPackageMapping> getAllMappings() {
        return userPackageMappingRepository.findAll();
    }

    @Override
    public List<UserPackageMapping> getMappingsByUserId(long userId) {
        if(!userRepository.existsById(userId)){
            throw new DataNotFoundException("Cannot find User");
        }
        return userPackageMappingRepository.findByUserId(userId);
    }

    @Override
    public List<UserPackageMapping> getMappingsByPackagePlanId(long packagePlanId) {
        if(!packagePlanRepository.existsById(packagePlanId)){
            throw new DataNotFoundException("Cannot find Package Plan");
        }
        return userPackageMappingRepository.findByPackagePlanId(packagePlanId);
    }

    @Override
    public UserPackageMapping createMapping(UserPackageMappingDTO userPackageMappingDTO) {
        User existingUser = userRepository.findById(userPackageMappingDTO.getUserId()).orElseThrow(()->new DataNotFoundException("Cannot find User"));
        PackagePlan existingPackagePlan = packagePlanRepository.findById(userPackageMappingDTO.getPackagePlanId()).orElseThrow(()->new DataNotFoundException("Cannot find Package Plan"));

        Optional<UserPackageMapping> existingUserPackageMapping =
                userPackageMappingRepository.findByUserIdAndPackagePlanId
                                (userPackageMappingDTO.getUserId(),userPackageMappingDTO.getPackagePlanId());
        if(existingUserPackageMapping.isPresent()){
            throw new DataValidationException("Da Ton Tia");
        }
        UserPackageMapping newUserPackageMapping = userPackageMappingMapper.toEntity(existingPackagePlan.getMaxEvents(),existingUser,existingPackagePlan);
        return userPackageMappingRepository.save(newUserPackageMapping);
    }

    @Override
    public UserPackageMapping updateMapping(UserPackageMappingDTO userPackageMappingDTO) {
        PackagePlan existingPackagePlan = packagePlanRepository.findById(userPackageMappingDTO.getPackagePlanId()).orElseThrow(()-> new DataNotFoundException("Cannot find Package Plan"));
        UserPackageMapping existingUserPackageMapping =
                userPackageMappingRepository.findByUserIdAndPackagePlanId
                        (userPackageMappingDTO.getUserId(),userPackageMappingDTO.getPackagePlanId())
                        .orElseThrow(()->new DataNotFoundException("Cannot find User Package Mapping"));

        existingUserPackageMapping.setNumberEvent(existingUserPackageMapping.getNumberEvent()+ existingPackagePlan.getMaxEvents());
        return userPackageMappingRepository.save(existingUserPackageMapping);
    }
    @Override
    public void deleteMapping(long userId, long packagePlanId) {
        Optional <UserPackageMapping> optionalUserPackageMapping = userPackageMappingRepository.findByUserIdAndPackagePlanId(userId,packagePlanId);
        optionalUserPackageMapping.ifPresent(userPackageMappingRepository::delete);
    }
}
