package Model;
import DAO.EmployeDAOimpl;

import java.io.File;
import java.util.List;

public class EmployeModel {
    private EmployeDAOimpl dao;
    public EmployeModel(EmployeDAOimpl dao) {
        this.dao = dao;
    }

    // funtion of add Employe :

    public boolean addEmploye(int id ,String nom, String prenom, String email, String telephone, double salaire, Role role, Post post, int solde) {
        if(salaire < 0 ){
            System.out.println("Erreur : le salaire doit etre positif.");
            return false;
        }
        if(id < 0 ){
            System.out.println("Erreur : l'id doit etre positif.");
            return false;
        }
        if(telephone.length() != 10){
            System.out.println("Erreur : le telephone doit etre 10 num.");
            return false;
        }
        if(!email.contains("@gmail.com")){
            System.out.println("Erreur : le mail doit contenir le @gmail.com .");
            return false;
        }

        Employe e = new Employe(id,nom, prenom, email, telephone, salaire, role, post ,solde);
        
        dao.add(e);

        return true;
    }

    // function of delete Employe :

    public  boolean deleteEmploye(int id){
        dao.delete(id);
        return true;
    }

    // function of update Employe :

    public boolean updateEmploye(int id, String nom, String prenom, String email, String telephone, double salaire, Role role, Post post , int solde) {

        Employe e = new Employe(id,nom, prenom, email, telephone, salaire, role, post,solde);
        dao.update(e);
        return true;
    }

    //function of update solde Employe :

    public boolean updateSolde(int id, int solde) {
        dao.updateSolde(id, solde);
        return true;
    }

    //function of display Employe :

    public List<Employe> displayEmploye() {
        List<Employe> Employes = dao.display();
        return Employes;
    }
private boolean checkFileExits(File file) {
		
		if(!file.exists()) {
			throw new IllegalArgumentException ("le fichier n'existe pas "+file.getPath());
			
		}
		return true;
		
	}
private boolean checkIsFile(File file) {
	
	if(!file.isFile()) {
		throw new IllegalArgumentException ("le chemin specifie nest pas un fichier "+file.getPath());
		
	}
	return true;
	
}
private boolean checkIsReadebal(File file) {
	
	if(!file.canRead()) {
		throw new IllegalArgumentException ("le chemin specifie nest pas lisibles "+file.getPath());
		
	}
	return true;
	
}
}
