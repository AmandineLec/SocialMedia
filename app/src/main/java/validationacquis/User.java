package validationacquis;
import validationacquis.utils.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;




public class User {


    //#region Variables
    protected int id;
    protected String identifiant;
    protected String password;
    protected String nom; 
    protected String email; 
    protected ArrayList<Post> posts;
    static Scanner scan;
    //#endregion

    //#region Constructeurs
    //Pour consulter un profil existant
    public User(int id){
        try {
            ResultSet resultat = DBManager.query("SELECT * FROM User WHERE User_id = "+id);
            if(resultat.next()){
                this.nom = resultat.getString("nom");
                this.identifiant = resultat.getString("identifiant");
                this.password = resultat.getString("password");
                this.email = resultat.getString("email");
                this.id = id;
                this.posts = new ArrayList<Post>(); 
                this.setPost(addpost());
                }
            }
        catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    public User(){
        this.posts = new ArrayList<Post>(); 
        this.setPost(addpost());
    }    
    
    //#region Getter and setter
    public int getId() {
        return id;    
    }
    public String getIdentifiant() {
        return identifiant;
    }
    public String getPassword() {
        return password;
    }
    public String getNom() {
        return nom;
    }
    public String getEmail() {
        return email;
    }    
    public ArrayList<Post> getPost() {
        return posts;
    }

    public void setPost(ArrayList<Post> posts) {
        this.posts = posts;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setEmail(String email) {
        this.email = email;
    }   
    //#endregion

    //#region METHOD
    public String toString(){
        if(this.getPost().isEmpty()){
            return "Bienvenue sur le profil de " + identifiant + "(" +nom+"). " + 
            "Vous n'avez encore rien publié comme post";        }
        else{ 
            return "Bienvenue sur le profil de " + identifiant + "("+ nom + "). " + 
                    "Voici les derniers post du profil : " + posts; 
        }
    }

    public ArrayList<Post> addpost(){
        int id = getId();
        int idpost;
        try {
            ResultSet resultat = DBManager.query("SELECT * FROM Post WHERE Post.User_id = "+ id);
            while(resultat.next()){
                idpost = resultat.getInt("id_post");
                Post post = new Post(idpost);
                this.getPost().add(post);
                }
            }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return this.getPost();
    }


    public Boolean liker(int idpost){

        try {
            PreparedStatement stmt = DBManager.conn.prepareStatement("INSERT INTO TableLike(User_id, Post_id) VALUES(?,?)");                    
                stmt.setInt(1, this.id);
                stmt.setInt(2, idpost);                    
                stmt.execute();   
                System.out.println("Merci ! Votre like a bien été ajouté !");    
                return true;         
        }catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
        }
    }

    public Boolean abonner(int iduser){
        try {
            PreparedStatement stmt = DBManager.conn.prepareStatement("INSERT INTO abonnement(User_id, follower_id) VALUES(?,?)"); 
            if(this.id != iduser){                   
                stmt.setInt(1, this.id);
                stmt.setInt(2, iduser);                    
                stmt.execute();   
            }
            System.out.println("Merci ! Vous vous êtes bien abonnés !");    
            return true; 
        }catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
        }
    }

    public void publierpost(String titre, String description) {
        Post statut = new Post(titre, description);
        statut.save(this.getId());
        System.out.println("Vous venez de publier votre statut : " + titre); 
    }

    public void saveUser(){
        try {
            scan = new Scanner(System.in);
            PreparedStatement stmt = DBManager.conn.prepareStatement("INSERT INTO User(identifiant, password, nom, email)"+
                                                                        " VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            System.out.println("Entrez votre identifiant");
            identifiant = scan.next();
            stmt.setString(1, identifiant);
            System.out.println("Entrez votre mot de passe");
            password = scan.next();
            stmt.setString(2, password);
            System.out.println("Entrez votre nom");
            nom = scan.next();
            stmt.setString(3, nom);         
            System.out.println("Entrez votre email");
            email = scan.next();
            stmt.setString(4, email);
            stmt.execute();
            ResultSet resultat = stmt.getGeneratedKeys();
            if (resultat.next())
                this.id = resultat.getInt(1);
            User user = new User();
            user = new User();
            user.setId(id);
            user.setIdentifiant(identifiant);
            user.setNom(nom);
            user.setPassword(password);
            user.setEmail(email);
            System.out.println("Vous venez de créer le profil de " + user);

        }catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    //#endregion
}



