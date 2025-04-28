package com.unu.web.repository;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.unu.web.entity.Banco;

@Repository("bancoRepository")
public interface BancoRepository extends JpaRepository<Banco, Serializable> {
	
}
