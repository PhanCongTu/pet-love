package com.stc.petlove.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stc.petlove.entities.embedded.ThongTinDatCho;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DatChoDto {

    // email người đặt chỗ
    private String email;

    // Thời gian chăm sóc thú cưng
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm")
    private Date thoiGian;

    // căn dặn khi chăm sóc thú cưng
    private String canDan;
}
