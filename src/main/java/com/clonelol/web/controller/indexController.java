//package com.clonelol.web.controller;
//
//import com.clonelol.champion.entity.Champion;
//import com.clonelol.web.dto.RotationResDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Controller
//public class indexController {
//
//    @GetMapping({"/","/index"})
//    public String getMain(Model model) {
//
//        rotationsRepository.findById("rotations").ifPresent(rot -> {
//            List<RotationResDto> rotations = rot.getFreeChampions()
//                    .stream()
//                    .map(Champion::toRotationResDto)
//                    .collect(Collectors.toList());
//            model.addAttribute("Rotations", rotations);
//        });
//
//        return "index";
//    }
//}
