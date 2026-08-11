package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient){
        PatientResponseDTO thePatientResponseDTO=new PatientResponseDTO();
        thePatientResponseDTO.setId(patient.getId().toString());
        thePatientResponseDTO.setName(patient.getName());
        thePatientResponseDTO.setEmail(patient.getEmail());
        thePatientResponseDTO.setAddress(patient.getAddress());
        thePatientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        return thePatientResponseDTO;
    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO){
        Patient patient=new Patient();
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        return patient;
    }
}
