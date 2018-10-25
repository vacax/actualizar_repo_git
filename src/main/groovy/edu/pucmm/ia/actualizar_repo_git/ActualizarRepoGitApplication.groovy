package edu.pucmm.ia.actualizar_repo_git

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class ActualizarRepoGitApplication {

    static void main(String[] args) {
        SpringApplication.run ActualizarRepoGitApplication, args
    }

    @RestController
    @RequestMapping("/")
    static class Actualizar{

        @Value('${token}')
        String tokenRegistrado
        @Value('${ruta_repositorio}')
        String rutaRepositorio

        @GetMapping("/")
        public String actualizarRepositorio(String token){
            String salida = ""
            if(tokenRegistrado != token){
              throw new RuntimeException("Token enviado no valido")
                return;
            }
            //ejecutando el comando
            String comando = "git pull"
            def env = []
            String salidaComando = comando.execute(env, new File(rutaRepositorio)).text
            salidaComando.eachLine {
                salida+="${it}<br/>"
            }
            println(salida)
            //
            return salida
        }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler([RuntimeException.class])
        String tokenNoValido(RuntimeException e){
           return e.getMessage()
        }

    }
}
