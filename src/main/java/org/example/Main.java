package org.example;

import org.example.controller.ReservaController;
import org.example.domain.IReservaRepository;
import org.example.infra.repository.SqlReservaRepository;
import org.example.service.ReservaService;
import org.example.service.ReservaServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

                IReservaRepository repo = new SqlReservaRepository();

                ReservaService service = new ReservaServiceImpl(repo);

                ReservaController controller = new ReservaController(service);

                String resultado = controller.agendarReserva("Matheus", "teste@email.com", "123", 200.0);
                System.out.println(resultado);
            }
        }
