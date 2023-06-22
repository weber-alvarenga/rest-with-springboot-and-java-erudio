package br.com.erudio.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class DozerMapper {

	// Alternativas ao Dozer: MapStruct, ModelMapper
	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	
	public static <ObjOrigem, ObjDestino> ObjDestino parseObject(ObjOrigem origem, Class<ObjDestino> destino) {
		
		return mapper.map(origem, destino);
		
	}
	
	
	public static <ObjOrigem, ObjDestino> List<ObjDestino> parseObjectsList(List<ObjOrigem> origem, Class<ObjDestino> destino) {
		
		List<ObjDestino> lstObjDestino = new ArrayList<ObjDestino>();
		
		for (ObjOrigem objOrigem : origem) {
			lstObjDestino.add(mapper.map(objOrigem, destino));
		}
		
		return lstObjDestino;
		
	}
	
}
