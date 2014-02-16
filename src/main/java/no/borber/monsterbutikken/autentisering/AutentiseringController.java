package no.borber.monsterbutikken.autentisering;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AutentiseringController {

    @Resource
    private HttpServletRequest httpRequest;

    @RequestMapping(value="autentisering/logginn/{brukernavn}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void loggInn( @PathVariable String brukernavn){
        httpRequest.getSession().setAttribute("bruker", brukernavn);
    }

    @RequestMapping(value="autentisering/innloggetBruker", method = RequestMethod.GET)
    @ResponseBody
    public Bruker getInnloggetBruker(){
        return new Bruker((String) httpRequest.getSession().getAttribute("bruker"));
    }
}
