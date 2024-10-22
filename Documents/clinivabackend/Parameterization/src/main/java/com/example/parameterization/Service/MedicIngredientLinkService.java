package com.example.parameterization.Service;

import com.example.parameterization.Repository.MedicIngredientLinkRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class MedicIngredientLinkService {
    private final MedicIngredientLinkRepo MIRepo;

}
