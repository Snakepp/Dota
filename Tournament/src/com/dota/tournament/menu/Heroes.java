package com.dota.tournament.menu;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dota.utils.PropertyManager;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;

public class Heroes {
	
	public Heroes(PropertyManager props){
		this.props = props;
	}

	private PropertyManager props;
	
	public ComboBox getHeroesComboBox(){
		ComboBox comboHeroes = new ComboBox();
		comboHeroes.setInputPrompt("select a hero");
		comboHeroes.addItems(getHeroes());
		comboHeroes.setNullSelectionAllowed(false);
		comboHeroes.setRequired(true);
		comboHeroes.setPageLength(3);
		comboHeroes.setFilteringMode(FilteringMode.CONTAINS);
		setIcons(comboHeroes);
		return comboHeroes;
	}
	
	private Set<String> getHeroes(){
		Set<String> heroes = new HashSet<String>();
		String filePath = props.getHeroesPath();
		
		File heroesDir = new File(filePath);
		for(File image : heroesDir.listFiles()){
			if(image.getName().contains(".jpg") || image.getName().contains(".gif")){
				String heroName = image.getName();
				heroName=heroName.substring(0,heroName.indexOf("."));
				heroes.add(heroName);
			}
		}
		
		return heroes;
	}
	
	private void setIcons(ComboBox heroes){
		String filePath =props.getIconHeroesPath();
		String extencion=".jpg";
		Map<String,FileResource> heroesMap = new HashMap<String, FileResource>();
		for(String hero : getHeroes()){
			heroesMap.put(hero, new FileResource(new File(filePath+hero+extencion)));
		}
		for(String hero : heroesMap.keySet()){
//			Image image = new Image();
//			image.setSource(heroesMap.get(hero));
//			FileResource fileRes = heroesMap.get(hero);
			heroes.setItemIcon(hero,heroesMap.get(hero));
		}
	}
	
}
