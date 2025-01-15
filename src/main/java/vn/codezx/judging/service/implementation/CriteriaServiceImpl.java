package vn.codezx.judging.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.codezx.judging.Exception.DataNotFoundException;
import vn.codezx.judging.dtos.CriteriaDTO;
import vn.codezx.judging.entity.Criteria;
import vn.codezx.judging.entity.Category;
import vn.codezx.judging.mapper.CriteriaMapper;
import vn.codezx.judging.repository.CategoryRepository;
import vn.codezx.judging.repository.CriteriaRepository;
import vn.codezx.judging.service.CriteriaService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CriteriaServiceImpl implements CriteriaService {

    private final CategoryRepository categoryRepository;
    private final CriteriaRepository criteriaRepository;
    private final CriteriaMapper criteriaMapper;

    @Override
    public Criteria createCriteria(CriteriaDTO criteriaDTO) {

        Category existingCategory = categoryRepository.findById(criteriaDTO.getCategoryId()).orElseThrow(()-> new DataNotFoundException("cannot find Category"));

        Criteria criteria = criteriaMapper.toEntity(criteriaDTO, existingCategory);

        // Save the Criteria entity in the database
        return criteriaRepository.save(criteria);
    }

    @Override
    public List<Criteria> getAllCriteriaByCategoryId(long categoryId) {
        // Fetch all Criteria for a given Category
        return criteriaRepository.findByCategoryId(categoryId);
    }

    @Override
    public Criteria getCriteriaById(long id) {
        // Fetch the Criteria by its ID
        Optional<Criteria> criteria = criteriaRepository.findById(id);
        return criteria.orElseThrow(() -> new RuntimeException("Criteria not found"));
    }

    @Override
    public Criteria updateCriteria(long id, CriteriaDTO criteriaDTO) {
        // Retrieve existing Criteria
        Criteria existingCriteria = getCriteriaById(id);

        Category existingCategory = categoryRepository.findById(criteriaDTO.getCategoryId()).orElseThrow(()-> new DataNotFoundException("cannot find Category"));
        // Update the fields from DTO to the entity
        existingCriteria.setCriteriaName(criteriaDTO.getCriteriaName());
        existingCriteria.setDescription(criteriaDTO.getDescription());
        existingCriteria.setWeight(criteriaDTO.getWeight());
        existingCriteria.setCategory(existingCategory);

        // Save the updated Criteria back to the database
        return criteriaRepository.save(existingCriteria);
    }

    @Override
    public void deleteCriteriaById(long id) {
        // Delete the Criteria by its ID
        criteriaRepository.deleteById(id);
    }
}
