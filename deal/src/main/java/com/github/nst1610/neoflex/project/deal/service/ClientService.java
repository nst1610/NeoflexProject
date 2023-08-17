package com.github.nst1610.neoflex.project.deal.service;

import com.github.nst1610.neoflex.project.deal.dto.FinishRegistrationRequestDTO;
import com.github.nst1610.neoflex.project.deal.dto.LoanApplicationRequestDTO;
import com.github.nst1610.neoflex.project.deal.entity.Client;
import com.github.nst1610.neoflex.project.deal.mapper.ClientMapper;
import com.github.nst1610.neoflex.project.deal.mapper.EmploymentMapper;
import com.github.nst1610.neoflex.project.deal.model.Passport;
import com.github.nst1610.neoflex.project.deal.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository repository;
    private final EmploymentMapper employmentMapper;
    private final ClientMapper clientMapper;

    @Transactional
    public void save(Client client) {
        repository.save(client);
    }

    public void get(Long id) {
        repository.findById(id);
    }

    public Client createClientFromLoanApplicationRequest(LoanApplicationRequestDTO loanApplicationRequest) {
        Client client = clientMapper.loanApplicationRequestToClient(loanApplicationRequest);
        Passport clientPassport = Passport.builder()
                .series(loanApplicationRequest.getPassportSeries())
                .number(loanApplicationRequest.getPassportNumber())
                .build();
        client.setPassport(clientPassport);
        return client;
    }

    public Client enrichClientInformation(Client client, FinishRegistrationRequestDTO finishRegistrationRequest) {
        client.setGender(finishRegistrationRequest.getGender());
        client.setMaritalStatus(finishRegistrationRequest.getMaritalStatus());
        client.setDependentAmount(finishRegistrationRequest.getDependentAmount());
        client.setAccount(finishRegistrationRequest.getAccount());
        client.getPassport().setIssueBranch(finishRegistrationRequest.getPassportIssueBranch());
        client.getPassport().setIssueDate(finishRegistrationRequest.getPassportIssueDate());
        client.setEmployment(employmentMapper.toModel(finishRegistrationRequest.getEmployment()));
        return client;
    }
}
