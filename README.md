# ShopPlugin pour AlgoBox

Bienvenue sur le git du plugin shop demandé par Algobox afin de lier son GUI à sa base de donnée. 

## Quelques informations

+ Plugin pour serveur 1.8.8
+ Plugin unitilisable seul (enfin il sert pas à grand chose)
+ Il faut appeler les méthodes principales

## Methode principales

+ public void vente (Player player, ItemStack item, Integer prix)
+ public void achat (Player player, ItemStack item)
+ public Vector<Vector> afficher(String cdt)

## Description des méthodes

+ vente: permet de vendre l'objet "item" aux prix "prix" par le joueur "player". Elle permet d'inserer une nouvelle ligne dans vente avec l'objet codé en base64.
+ achat: permet l'achat d'objet avec une actualisation des lignes de l'acheteur et du vendeur et supprime l'item de la table vente.
+ afficher: renvoie un vecteur de vecteur contenant l'uuid, le prix, et l'item (ItemStack) de la base de donnée de vente.

## Format de la base de donnée utilisé

+ monnaie (*uuid*, pseudo, balance)
+ vente (uuid, prix, objet)
