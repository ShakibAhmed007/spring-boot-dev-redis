package com.example.dev.springbootdev.controller;

import com.example.dev.springbootdev.entities.Accounts;
import com.example.dev.springbootdev.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @GetMapping("/getAll")
    public List<Accounts> getAll() {
        return accountsService.getAll();
    }

    @GetMapping("/getAllByPagination")
    public ResponseEntity<Object> getAll(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "15") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "false") Boolean isAscending) {
        Page<Accounts> page = accountsService.getAllByPagination(pageNumber, pageSize, sortBy, isAscending);
        List<Accounts> accounts = page.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("accounts", accounts);
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllByEmail")
    public List<Accounts> getAllByEmail(@RequestParam String email) {
        return accountsService.getAllByEmail(email);
    }

    @GetMapping("/getAllByRole")
    public List<Accounts> getAllByRole(@RequestParam String role) {
        return accountsService.getAllByRole(role);
    }

    @GetMapping("/getById/{id}")
    public Optional<Accounts> getById(@PathVariable Long id) {
        return accountsService.getById(id);
    }

    @PostMapping("/saveDummyAccounts/{no}")
    public ResponseEntity<Object> saveDummyAccounts(@PathVariable Integer no)throws Exception{
        int x = 0;
        int limit = (x + no);
        Accounts[] accounts = new Accounts[limit];
        while (x < limit) {
            UUID uuid = UUID.randomUUID();
            Accounts account = new Accounts();
            account.setUserName("shakib" + uuid.toString());
            account.setUserEmail("shakkib" +  uuid.toString() + "@gmail.com");
            account.setPassword("xcxcvsdfsdgsdg");
            account.setUserAge(x);
            account.setUserRole("ADMIN");
            account.setStatusId(1);
            account.setCreatedOn(LocalDateTime.now());
            account.setUpdatedOn(LocalDateTime.now());
            account.setLastLogin(LocalDateTime.now());
            accounts[x] = account;
            x++;
        }
        try {
            accountsService.saveAll(Arrays.asList(accounts));
        } catch (Exception e) {
            System.out.println(e);
        }
        // add location in response header
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("100").toUri(); // todo 100 should be from db
        return ResponseEntity.created(location).build();
    }
    @PostMapping("/add")
    public ResponseEntity<Object> add(@Valid @RequestBody Accounts account) throws Exception {
        accountsService.add(account);
        // add location in response header
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("100").toUri(); // todo 100 should be from db
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/edit")
    public ResponseEntity<Object> edit(@Valid @RequestBody Accounts account) throws Exception {
        return ResponseEntity.ok(accountsService.edit(account, account.getId()));
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Object> deleteAll() throws Exception {
        accountsService.deleteAll();
        return ResponseEntity.ok("Deleted Successfully");
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) throws Exception {
        accountsService.deleteById(id);
        return ResponseEntity.ok("Deleted By Id Successfully");
    }
}
