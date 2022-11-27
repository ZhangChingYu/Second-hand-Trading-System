package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.exchangedto.ExRequestDto;
import dev.silvia.wechattrade.dto.exchangedto.ExchangeDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.entity.Booking;
import dev.silvia.wechattrade.entity.ExchangeInfo;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.common.repository.BookingRespository;
import dev.silvia.wechattrade.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService extends ServiceImpl<UserDao, User> implements IOrderService {

    @Autowired
    private BookingRespository accountRepository;

    private Result res;

    @Autowired
    private Optional<Booking> user;

    private List<ExchangeDto> addre=new ArrayList<>();


    private ExchangeInfo user2;
    @Override
    public Optional<ExchangeDto> build(ExRequestDto request) {
        return Optional.empty();
    }



    @Override
    public Optional<ExchangeDto> addappointments(ExRequestDto request) {
        return Optional.empty();
    }
}
