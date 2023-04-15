package com.stc.petlove;

import com.stc.petlove.entities.TaiKhoan;
import com.stc.petlove.repositories.TaiKhoanRepository;
import com.stc.petlove.utils.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class PetLoveApplication implements CommandLineRunner {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    public static void main(String[] args) {
        SpringApplication.run(PetLoveApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(taiKhoanRepository.count()==0){
            TaiKhoan user = new TaiKhoan("admin01","admin01@gmail.com","123456","0123456789",
                    Arrays.asList(EnumRole.ROLE_ADMIN.name()));
            TaiKhoan user1 = new TaiKhoan("user01","user01@gmail.com","123456","0123456789",
                    Arrays.asList(EnumRole.ROLE_USER.name()));
            TaiKhoan user2 = new TaiKhoan("userAdmin01","userAdmin01@gmail.com","123456","0123456789",
                    Arrays.asList(EnumRole.ROLE_USER.name(), EnumRole.ROLE_ADMIN.name()));
            taiKhoanRepository.save(user);
            taiKhoanRepository.save(user1);
            taiKhoanRepository.save(user2);
        }
    }
}
