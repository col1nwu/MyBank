package priv.cwu.mybank.springboot.dao.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_account")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "account_holder_id")
    private Long accountHolderId;

    @Column(name = "balance")
    private Double balance = 0.0;

    @Column(name = "is_locked")
    private Boolean isLocked = false;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;


    public Account(String accountId, Long accountHolderId) {
        this.accountId = accountId;
        this.accountHolderId = accountHolderId;
    }

}
