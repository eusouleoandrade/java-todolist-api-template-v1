package com.mycompany.javatodolistapitemplatev1;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // The database connection settings are in the properties
    //
    // @Bean
    // DataSource dataSource() {
    // DriverManagerDataSource dataSource = new DriverManagerDataSource();
    // dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    // dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/todolistdb");
    // dataSource.setUsername("root");
    // dataSource.setPassword("pwdmysql");
    // return dataSource;
    // }
}