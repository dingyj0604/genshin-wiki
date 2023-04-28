package com.example.genshinwiki.infrastructure.common.entity;

import com.example.genshinwiki.infrastructure.common.support.SnowflakeIdGenerator;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体基类
 *
 * @author ankelen
 * @date 2020-05-18 20:36
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 数据库 id
     */
    @Id
    @Column(length = 20)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SNOWFLAKE")
    @GenericGenerator(name = "SNOWFLAKE", strategy = SnowflakeIdGenerator.TYPE)
    private String id;


    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;
    /**
     * 最后更新时间
     */
    @UpdateTimestamp
    private LocalDateTime updateAt;

    //region equals,hashCode,toString

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseEntity that = (BaseEntity) o;
        // 两个id为null的entity会被认为是同一个实体
        return this.getId() != null ? this.id.equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return this.getId() != null ? this.getId().hashCode() : System.identityHashCode(this);
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id='" + id + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt + '\'' +
                '}';
    }

    //endregion
}
