package com.tinyurl.repository;

import com.tinyurl.model.ShortenUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortenUrlRepository extends JpaRepository<ShortenUrl, String> {

}



