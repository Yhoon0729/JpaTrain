package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    // 1. GET으로 리스트 보기
    public List<Coffee> findAll() {
        List<Coffee> coffeeList = coffeeRepository.findAll();
        return coffeeList;
    }

    // 2. POST로 데이터 추가하기
    public Coffee save(CoffeeDto dto) {
        Coffee coffee = dto.toEntity();
        log.info("dto 값 =" + dto);
        log.info("dto 값 넣은 엔티티 = " + coffee);


        return coffeeRepository.save(coffee);
    }

    // 3. PATCH로 데이터 수정하기
    public Coffee update(long id, CoffeeDto dto) {
        // 1. dto => entity
        Coffee coffee = dto.toEntity();

        // 2. 타겟 찾기
        Coffee target = coffeeRepository.findById(id).orElse(null);
        log.info("타겟 = " + target);

        // 3. 잘못된 요청 처리하기
        if (target == null || id != coffee.getId()) {
            return null;
        }

        // 4. 수정 후 리턴
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);

        return updated;
    }

    // 4. DELETE로 데이터 삭제하기
    public Coffee delete(long id) {
        // 1. 삭제할 타겟 찾기
        Coffee target = coffeeRepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리하기
        if(target == null) {
            return null;
        }

        // 3. 타겟 데이터 삭제하기
        coffeeRepository.delete(target);
        return target;
    }
}
