package com.example.apiRelatorios.controller;

import com.example.apiRelatorios.service.RelatorioInspecaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin("*")

public class ControllerRelatorio {

    @Autowired
    private RelatorioInspecaoService relatorioInspecaoService;

    @GetMapping("/relatorio-inspecaoRemota")
    public ResponseEntity<byte[]>relatorioInspecao(@RequestParam(value = "id", required = true,defaultValue = "0") Long id){

        byte[] relatorioGerado = relatorioInspecaoService.gerarRelatorio(id);

        HttpHeaders headers = new HttpHeaders();
        String fileName = "relatorio-inspecao.pdf";


        headers.setContentDispositionFormData("inline; filename=\" " +fileName+ "\"",fileName);

        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        var responseEntity = new ResponseEntity<>(relatorioGerado,headers, HttpStatus.OK);
        return responseEntity;

    }

}
