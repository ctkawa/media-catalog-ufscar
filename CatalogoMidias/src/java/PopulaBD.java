
import br.ufscar.dc.entidade.Papel;
import br.ufscar.dc.entidade.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.cfg.Configuration;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class PopulaBD {

    private static PopulaBD instance;
    private EntityManager em;

    public static PopulaBD getInstance() {

        if (instance == null) {
            instance = new PopulaBD();
        }
        return instance;
    }

    private PopulaBD() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CatalogoMidiasPU");
        em = emf.createEntityManager();
    }
    
//    private void populaCidades() {
//
//        Map<String, Estado> map = new HashMap<String, Estado>();
//
//        String line;
//        String nome;
//        Estado estado;
//        String sigla;
//
//        InputStream stream = PopulaBD.class.getResourceAsStream("/cidades.txt");
//        InputStreamReader isr = new InputStreamReader(stream);
//        BufferedReader reader = new BufferedReader(isr);
//
//        try {
//            line = reader.readLine();
//            while (line != null) {
//                StringTokenizer tokenizer = new StringTokenizer(line, ",");
//                sigla = tokenizer.nextToken();
//                sigla = sigla.substring(1, 3);
//                nome = tokenizer.nextToken();
//                nome = nome.substring(1, nome.length() - 1);
//                if (map.containsKey(sigla)) {
//                    estado = map.get(sigla);
//                } else {
//                    Query q = em.createNamedQuery("Estado.findBySigla");
//                    q.setParameter("sigla", sigla);
//                    estado = (Estado) q.getSingleResult();
//                    map.put(estado.getSigla(), estado);
//                }
//
//                Cidade cidade = new Cidade();
//                cidade.setNome(nome);
//                cidade.setEstado(estado);
//
//                em.getTransaction().begin();
//                em.persist(cidade);
//                System.out.println("Salvo: " + cidade);
//                em.getTransaction().commit();
//
//                line = reader.readLine();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
    
    private void populaPapel() {
        
        Papel papel = new Papel();
        papel.setNome("Teste");
        papel.setPapelNoFilme("Qualquer");

        em.getTransaction().begin();
        em.persist(papel);
        System.out.println("Salvo: " + papel);
        em.getTransaction().commit();
        
    }
//
    private void populaUsuarios() {
        try {

            Usuario admin = new Usuario();
            admin.setLogin("admin");
            admin.setSenha("admin");
            admin.setNome("Administrador");

            em.getTransaction().begin();
            em.persist(admin);
            System.out.println("Salvo: " + admin);
            em.getTransaction().commit();

            Usuario maria = new Usuario();
            maria.setLogin("maria");
            maria.setSenha("maria");
            maria.setNome("Maria José da Silva");

            em.getTransaction().begin();
            em.persist(maria);
            System.out.println("Salvo: " + maria);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Ejb3Configuration cfg = new Ejb3Configuration();

        cfg.configure("CatalogoMidiasPU", null);
        Configuration hbmcfg = cfg.getHibernateConfiguration();

        SchemaExport schemaExport = new SchemaExport(hbmcfg);
        schemaExport.create(true, true);

        PopulaBD obj = PopulaBD.getInstance();
        
        obj.populaPapel();
        obj.populaUsuarios();
//        obj.populaEstados();
//        obj.populaCidades();
    }
}
