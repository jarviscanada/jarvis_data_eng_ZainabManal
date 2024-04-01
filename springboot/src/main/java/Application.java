import controller.QuoteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(exclude = {JdbcTemplateAutoConfiguration.class, DataSourceAutoConfiguration.class,
HibernateJpaAutoConfiguration.class})

    public class Application implements CommandLineRunner
    {
        private Logger logger = LoggerFactory.getLogger(Application.class);

        @Value("${app.init.dailyList}")
        private String initDailyList;

        @Autowired
        QuoteController quoteController;


        public static void main(String[] args)
        {
            SpringApplication app = new SpringApplication(Application.class);
            app.run(args);
        }

        @Override
        public void run(String... args) throws Exception {

        }
    }

