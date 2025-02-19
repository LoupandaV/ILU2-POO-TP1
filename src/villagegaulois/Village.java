package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum,int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	 public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		 int indiceEtalLibre=marche.trouverEtalLibre();
		 
		 StringBuilder chaine = new StringBuilder();
		 chaine.append(vendeur + "cherche un endroit pour vendre "+nbProduit+ produit+ ".\n");
			if (indiceEtalLibre != -1) {
				marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
				chaine.append("Le vendeur"+vendeur+" vend des "+produit+" à l'étal n°"+indiceEtalLibre+".\n");
			} else {
				chaine.append("Il n'y a pas d'étale non utiliser.\n");
			}
			
			return chaine.toString();
		 
	 }
	
	 public String rechercherVendeursProduit(String produit){
		 Etal[] etalsProduit = marche.trouverEtals(produit);
		 
		 StringBuilder chaine = new StringBuilder();
		 if (etalsProduit.length <= -0) {
			chaine.append("Il n'y a pas de vendeur qui propose des "+produit+" au marché.\n");
			} else if (etalsProduit.length <= 1) {
				chaine.append("Seul le vendeur "+etalsProduit[0].getVendeur()+"propose des"+produit+" au marché.\n");
			}else {
				chaine.append("Les vendeurs qui proposent des "+produit+" sont :\n");
				for (int i = 0; i < etalsProduit.length; i++) {
					chaine.append("- "+etalsProduit[i].getVendeur()+"\n");
				}
			}
			
			return chaine.toString();
	 }
	 
	 public String partirVendeur(Gaulois vendeur) {
		 Etal etalVendeur = marche.trouverVendeur(vendeur);
		 return etalVendeur.libererEtal();
		 
	 }
	
	private class Marche{
		private Etal[] etals;
		private int nbEtals;
		
		private Marche(int nbEtals) {
			this.nbEtals=nbEtals;
			etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i]=new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit){
			etals[indiceEtal].occuperEtal(vendeur,produit,nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < this.nbEtals; i++) {
				if (! etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtalsProduit=0;
			for (int i = 0; i < this.nbEtals; i++) {
				if (etals[i].contientProduit(produit)) {
					nbEtalsProduit++;
				}
			}
		Etal[] etalsProduit = new Etal[nbEtalsProduit];
			int indiceEtalsProduit=0;
			for (int i = 0; i < this.nbEtals; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsProduit[indiceEtalsProduit]=etals[i];
					indiceEtalsProduit++;
				}
			}
			
			return etalsProduit;	
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < this.nbEtals; i++) {
				if (etals[i].getVendeur()==gaulois) {
					return etals[i];
				}
			}
			return null;
			
		}
			
		private void afficherMarche() {
			int nbEtalVide=nbEtals;
			for (int i = 0; i < this.nbEtals; i++) {
				if (etals[i].isEtalOccupe()) {
					etals[i].afficherEtal();
					nbEtalVide--;
				}
			}
			if (nbEtalVide > 0) {
				System.out.println("Il reste " +nbEtalVide +"étals non utilisés dans le marché.\n");
			}
		}

			
	
	
	}
}