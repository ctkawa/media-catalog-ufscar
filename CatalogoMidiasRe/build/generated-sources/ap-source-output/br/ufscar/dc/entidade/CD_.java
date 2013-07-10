package br.ufscar.dc.entidade;

import br.ufscar.dc.entidade.Faixa;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-07-10T14:30:08")
@StaticMetamodel(CD.class)
public class CD_ extends Midia_ {

    public static volatile CollectionAttribute<CD, Faixa> Faixas;
    public static volatile SingularAttribute<CD, String> artista;

}