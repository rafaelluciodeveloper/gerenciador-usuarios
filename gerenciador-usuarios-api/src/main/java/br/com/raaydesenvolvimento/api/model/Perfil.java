package br.com.raaydesenvolvimento.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "perfis")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false)
    private String descricao;

    @ManyToMany(mappedBy = "perfis", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Usuario> usuarios = new HashSet<>();

}
