package com.aburilovic.springbootsandbox.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "device")
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hardwareId;
    private String sku;
    private String description;
}
