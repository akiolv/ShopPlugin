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
+ public deleteObjet(Player player, ItemStack item)

## Description des méthodes

+ vente: permet de vendre l'objet "item" aux prix "prix" par le joueur "player". Elle permet d'inserer une nouvelle ligne dans vente avec l'objet codé en base64.
+ achat: permet l'achat d'objet avec une actualisation des lignes de l'acheteur et du vendeur et supprime l'item de la table vente.
+ afficher: renvoie un vecteur de vecteur contenant l'uuid, le prix, et l'item (ItemStack) de la base de donnée de vente.
+ deleteObjet: permet de supprimer un item de l'inventaire d'un joueur (gères les stacks)

## Format de la base de donnée utilisé

+ monnaie (*uuid*, pseudo, balance)
+ vente (uuid, prix, objet)

## Crédit

Codé par akiolv
