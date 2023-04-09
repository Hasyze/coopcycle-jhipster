import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Client from './client';
import Livreur from './livreur';
import Livraison from './livraison';
import Restaurant from './restaurant';
import Produit from './produit';
import Association from './association';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="client/*" element={<Client />} />
        <Route path="livreur/*" element={<Livreur />} />
        <Route path="livraison/*" element={<Livraison />} />
        <Route path="restaurant/*" element={<Restaurant />} />
        <Route path="produit/*" element={<Produit />} />
        <Route path="association/*" element={<Association />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
