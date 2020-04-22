package com.diogoamsilva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diogoamsilva.converter.DozerConverter;
import com.diogoamsilva.dto.PersonDTO;
import com.diogoamsilva.exception.ResourceNotFoundException;
import com.diogoamsilva.model.Person;
import com.diogoamsilva.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	PersonRepository repository;
	
	public PersonDTO create (PersonDTO personDTO) {
		Person person = DozerConverter.parseObject(personDTO, Person.class);
		return DozerConverter.parseObject(repository.save(person), PersonDTO.class);
	}
	
	public Page<PersonDTO> findAll(Pageable pageable) {
		var page = repository.findAll(pageable);
		return page.map(this::convertToPersonDTO);
	}
	
	public Page<PersonDTO> findPersonByName(String firstName, Pageable pageable) {
		var page = repository.findPersonByName(firstName, pageable);
		return page.map(this::convertToPersonDTO);
	}
	
	private PersonDTO convertToPersonDTO(Person entity)  {
		return DozerConverter.parseObject(entity, PersonDTO.class);
	}
	
	public PersonDTO findById(Long id) {
		Person person = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No record found for this ID"));
		return DozerConverter.parseObject(person, PersonDTO.class);
	}
	
	public PersonDTO update (PersonDTO personDTO) {
		Person person = repository.findById(personDTO.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No record found for this ID"));

		person.setFirstName(personDTO.getFirstName());
		person.setLastName(personDTO.getLastName());
		person.setAddress(personDTO.getAddress());
		person.setGender(personDTO.getGender());
		
		return DozerConverter.parseObject(repository.save(person), PersonDTO.class);
	}
	
	@Transactional
	public PersonDTO disablePerson(Long id) {
		repository.disablePerson(id);
		Person person = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No record found for this ID"));
		return DozerConverter.parseObject(person, PersonDTO.class);
	}
	
	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No record found for this ID"));
		repository.delete(entity);
	}
	
}
