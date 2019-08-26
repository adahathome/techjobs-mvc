package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    static LinkedHashMap<String, String> searchChoices = new LinkedHashMap<>();

    public SearchController () {
        searchChoices.put("name", "Position Title");
        searchChoices.put("employer", "Employer");
        searchChoices.put("location", "Location");
        searchChoices.put("positiontype", "Position Type");
        searchChoices.put("corecompetency", "Skill");


    }

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", SearchController.searchChoices);
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results
    @RequestMapping(value = "results")
    public String results(Model model, @RequestParam String searchType,
                           @RequestParam String searchTerm) {
        model.addAttribute("columns", SearchController.searchChoices);
        if (searchType.equals("all")) {
            ArrayList<HashMap<String, String>> jobs = JobData.findByValue(searchTerm);
            model.addAttribute("subHeader", "All Search Results for " + searchTerm);
            model.addAttribute("jobs", jobs);
            return "search";
        } else {
            ArrayList<HashMap<String, String>> jobs = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("subHeader", "All " +
                    SearchController.searchChoices.get(searchType) + " Results for " + searchTerm );
            model.addAttribute("column", searchType);
            model.addAttribute("jobs", jobs);
            return "search";
        }
    }

}
