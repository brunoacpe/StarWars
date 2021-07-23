package br.com.letscode.Controller;


import br.com.letscode.Services.RebeldesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
@RequestMapping("/reportar")
public class ReportsController {

    private RebeldesServices rebeldesServices;

    @Autowired
    public ReportsController(RebeldesServices rebeldesServices){
        this.rebeldesServices = rebeldesServices;
    }

    @PostMapping
    public String reportarRebelde(@RequestParam String nomeReportador, @RequestParam String nomeReportado) throws IOException {
        return rebeldesServices.reportarRebelde(nomeReportador,nomeReportado);
    }

}
