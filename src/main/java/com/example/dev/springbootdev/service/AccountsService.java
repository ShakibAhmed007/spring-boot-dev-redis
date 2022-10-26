package com.example.dev.springbootdev.service;

import com.example.dev.springbootdev.entities.Accounts;
import com.example.dev.springbootdev.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    public void add(Accounts acc) throws Exception{
        accountsRepository.save(acc);
    }

    public void saveAll(List<Accounts> accounts){
        accountsRepository.saveAll(accounts);
    }

    @CachePut(value = "Account", key="#id")
    public Accounts edit(Accounts acc, Long id) throws Exception{
        return accountsRepository.save(acc);
    }

    public List<Accounts> getAll(){
        return (List<Accounts>) accountsRepository.findAll();
    }

    public List<Accounts> getAllByEmail(String email){
        return accountsRepository.findByUserEmail(email);
    }

    public List<Accounts> getAllByRole(String role){
        return accountsRepository.findByUserRole(role);
    }

    @Cacheable(value = "Accounts")
    public Page<Accounts> getAllByPagination(Integer pageNumber, Integer pageSize, String sortBy, Boolean isAscending){
        Sort sort = Sort.by(sortBy).descending();
        if(isAscending)
            sort = Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);
        return accountsRepository.findAll(pageable);
    }

    @Cacheable(value = "Account", key = "#id")
    public Optional<Accounts> getById(Long id){
        Optional<Accounts> optional = accountsRepository.findById(id);
        return optional.stream().findFirst();
    }

    public void deleteAll()throws Exception{
        accountsRepository.deleteAll();
    }

    @CacheEvict(value = "Account", key = "#id")
    public void deleteById(Long id)throws Exception{
        accountsRepository.deleteById(id);
    }
}
