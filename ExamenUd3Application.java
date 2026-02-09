package goya.daw2.dwes.ud3;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class ExamenUd3Application { // implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ExamenUd3Application.class, args);
	}
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	/*
	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		for (String password : List.of("manzana","pera","plátano","lechuga","lenteja","judía")) {
			System.out.println(encoder.encode(password));
		}
		
	}
	*/

	

}
