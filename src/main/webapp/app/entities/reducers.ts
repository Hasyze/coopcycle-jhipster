import client from 'app/entities/client/client.reducer';
import livreur from 'app/entities/livreur/livreur.reducer';
import livraison from 'app/entities/livraison/livraison.reducer';
import restaurant from 'app/entities/restaurant/restaurant.reducer';
import produit from 'app/entities/produit/produit.reducer';
import association from 'app/entities/association/association.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  client,
  livreur,
  livraison,
  restaurant,
  produit,
  association,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
