package com.wcamprog.apispringdev.infrastructure.persistence;

import com.wcamprog.apispringdev.domain.Franquicia;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FranquiciaReactiveRepository extends ReactiveMongoRepository<Franquicia, String> {
}
