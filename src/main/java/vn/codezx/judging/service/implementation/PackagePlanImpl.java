package vn.codezx.judging.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.codezx.judging.Exception.AlreadyExistsException;
import vn.codezx.judging.Exception.DataNotFoundException;
import vn.codezx.judging.dtos.PackagePlanDTO;
import vn.codezx.judging.entity.PackagePlan;
import vn.codezx.judging.mapper.PackagePlanMapper;
import vn.codezx.judging.repository.PackagePlanRepository;
import vn.codezx.judging.service.PackagePlanService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackagePlanImpl implements PackagePlanService {

    private final PackagePlanRepository packagePlanRepository;
    private final PackagePlanMapper packagePlanMapper;

    @Override
    public PackagePlan createPackagePlan(PackagePlanDTO packagePlanDTO) {
        if (packagePlanRepository.existsByPlanName(packagePlanDTO.getPlanName())) {
            throw new AlreadyExistsException("PackagePlan already exists");
        }
        PackagePlan newPackagePlan = packagePlanMapper.toEntity(packagePlanDTO);
        return packagePlanRepository.save(newPackagePlan);
    }

    @Override
    public List<PackagePlan> getAllPackagePlan() {
        return packagePlanRepository.findAll();
    }

    @Override
    public PackagePlan getPackagePlanByName(String name) {
        return packagePlanRepository.findByPlanName(name)
                .orElseThrow(()-> new DataNotFoundException("Cannot find PackagePlan"));
    }

    @Override
    public PackagePlan getPackagePlanById(long id) {
        return packagePlanRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Cannot find PackagePlan"));
    }

    @Override
    public PackagePlan updatePackagePlanById(long id, PackagePlanDTO packagePlanDTO) {
        PackagePlan existingPackagePlan = getPackagePlanById(id);

        if (packagePlanDTO.getPlanName() != null && !packagePlanDTO.getPlanName().equals(existingPackagePlan.getPlanName())) {
            if(packagePlanRepository.existsByPlanName(packagePlanDTO.getPlanName())){
                throw new AlreadyExistsException("PackagePlan already exists");
            }
            existingPackagePlan.setPlanName(packagePlanDTO.getPlanName());
        }

        if (packagePlanDTO.getDescription() != null &&
                !packagePlanDTO.getDescription().equals(existingPackagePlan.getDescription())) {
            existingPackagePlan.setDescription(packagePlanDTO.getDescription());
        }

        if (packagePlanDTO.getMaxEvents() != 0 && packagePlanDTO.getMaxEvents() != existingPackagePlan.getMaxEvents()) {
            existingPackagePlan.setMaxEvents(packagePlanDTO.getMaxEvents());
        }

        if (packagePlanDTO.getMaxJudges() != 0 && packagePlanDTO.getMaxJudges() != existingPackagePlan.getMaxJudges()) {
            existingPackagePlan.setMaxJudges(packagePlanDTO.getMaxJudges());
        }

        if (packagePlanDTO.getMaxProjects() != 0 && packagePlanDTO.getMaxProjects() != existingPackagePlan.getMaxProjects()) {
            existingPackagePlan.setMaxProjects(packagePlanDTO.getMaxProjects());
        }
        return packagePlanRepository.save(existingPackagePlan);
    }

    @Override
    public void deletePackagePlanById(long id) {
        Optional<PackagePlan> optionalPackagePlan = packagePlanRepository.findById(id);
        optionalPackagePlan.ifPresent(packagePlanRepository::delete);
    }

    @Override
    public boolean existsByPackagePlanName(String name) {
        return existsByPackagePlanName(name);
    }
}
