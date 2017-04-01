package protectedarea.bigdata.controllers;

import java.util.Collection;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import protectedarea.bigdata.model.Area;
import protectedarea.bigdata.model.Event;
import protectedarea.bigdata.repository.AreaMongoRepository;
import protectedarea.bigdata.repository.AreaSearchRepository;
import protectedarea.bigdata.repository.EventMongoRepository;
import protectedarea.bigdata.repository.EventSearchRepository;

@Controller
public class AreaController {

	@Autowired
	AreaMongoRepository areaRepository;
	
	@Autowired
	AreaSearchRepository areaSearchRepository;
	
	@Autowired
	EventMongoRepository eventRepository;
	
	@Autowired
	EventSearchRepository eventSearchRepository;
	
	@RequestMapping("/home")
	public String home(Model model) {
		//model.addAttribute("areaList", areaRepository.findAll());
		return "home";
	}
	@RequestMapping(value = "/search", method=RequestMethod.POST)
	public String search(Model model, @RequestParam String search) {
		Collection<Area> results = areaSearchRepository.searchArea(search);
		
		Area temp = null;
		
		for (Area a : results) {
	        temp = a;
	        break;
	    }
		results.clear();
		results.add(temp);
		
		Collection<Event> events = eventSearchRepository.searchEvent(temp.getSUBLOC(), temp.getLAT(), temp.getLON());
		Gson gson = new Gson();
		String eventList = gson.toJson(events);

		model.addAttribute("eventList", eventList);
		model.addAttribute("areaList", results);
		model.addAttribute("search", search);
		return "home";
	}
	
	
}
