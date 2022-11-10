package com.example.bldonate.services.impl;

import com.example.bldonate.models.dto.JwtUser;
import com.example.bldonate.models.entities.KorisnikEntity;
import com.example.bldonate.repositories.KorisnikRepository;
import com.example.bldonate.services.JwtUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsImpl implements JwtUserDetailsService {


    private final KorisnikRepository korisnikRepository;
    private final ModelMapper mapper;

    public JwtUserDetailsImpl(KorisnikRepository korisnikRepository, ModelMapper mapper) {
        this.korisnikRepository = korisnikRepository;
        this.mapper = mapper;
    }

    @Override
    public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {

        return mapper.map(korisnikRepository.findByKorisnickoImeAndStatus(username, KorisnikEntity.Status.ACTIVE).
                orElseThrow(() -> new UsernameNotFoundException(username)), JwtUser.class);
    }
}
