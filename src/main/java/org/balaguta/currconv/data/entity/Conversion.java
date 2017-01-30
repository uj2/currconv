package org.balaguta.currconv.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Conversion {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime timestamp;

    @ManyToOne
    private User user;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "SOURCE_AMOUNT")),
            @AttributeOverride(name = "currency", column = @Column(name = "SOURCE_CURRENCY"))
    })
    private MoneyAmount source;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "TARGET_AMOUNT")),
            @AttributeOverride(name = "currency", column = @Column(name = "TARGET_CURRENCY"))
    })
    private MoneyAmount target;
}
