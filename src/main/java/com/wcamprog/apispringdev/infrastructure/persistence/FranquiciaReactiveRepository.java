package com.wcamprog.apispringdev.infrastructure;

import com.wcamprog.apispringdev.domain.Franquicia;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FranquiciaReactiveRepository extends ReactiveMongoRepository<Franquicia, String> {
}
