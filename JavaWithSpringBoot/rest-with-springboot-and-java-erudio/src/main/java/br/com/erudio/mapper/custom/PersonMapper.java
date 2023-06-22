package br.com.erudio.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.erudio.data.vo.v2.PersonVOv2;
import br.com.erudio.model.Person;

@Service
public class PersonMapper {

	public PersonVOv2 convertEntytyToVo(Person person) {
		
		PersonVOv2 vo = new PersonVOv2();
		vo.setId(person.getId());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setBirthDay(new Date());
		vo.setAddress(person.getAddress());
		vo.setGender(person.getGender());
		
		return vo;
	}
	
	
	public Person convertVoToEntity(PersonVOv2 vo) {
		
		Person entity = new Person();
		entity.setId(vo.getId());
		entity.setFirstName(vo.getFirstName());
		entity.setLastName(vo.getLastName());
		//entity.setBirthDay(new Date());
		entity.setAddress(vo.getAddress());
		entity.setGender(vo.getGender());
		
		return entity;
	}
	
}
