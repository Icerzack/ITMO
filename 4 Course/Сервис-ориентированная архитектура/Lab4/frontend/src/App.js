import React, { useEffect, useState } from 'react';
import { RoutesTable } from './components/RoutesTable';
import { ModalWindow } from './components/ModalWindow';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import { DeleteRouteButton } from './components/DeleteRouteButton';
import { DijkstraForm } from './components/navigator/DijkstraForm';
import { FiltersForm } from './components/table/FiltersForm';
import { SortForm } from './components/table/SortForm';
import toast, { Toaster } from 'react-hot-toast';
import {
  filtersState,
  isDataNeedsToBeUpdatedState,
  oldPagingState,
  pagingState,
  routesState,
  sortState
} from './state/atoms';
import { getRoutes } from './utils/apiInteraction';
import get from 'lodash.get';
import { ReloadButton } from './components/ReloadButton';
import { AddRoute } from './components/navigator/AddRoute';
import { DeleteAllRoutes } from './components/routes/DeleteAllRoutes';
import { CountAllRoutes } from './components/routes/CountAllRoutes';
import { CountDistanceGreater } from './components/routes/CountDistanceGreater';
import { PagingForm } from './components/table/PagingForm';
import set from 'lodash.set';

function App() {
  const setRoutes = useSetRecoilState(routesState);
  const [isDataNeedsToBeUpdated, setIsDataNeedsToBeUpdated] = useRecoilState(
    isDataNeedsToBeUpdatedState
  );
  const sort = useRecoilValue(sortState);
  const filters = useRecoilValue(filtersState);
  const [paging, setPaging] = useRecoilState(pagingState);
  const [oldPaging] = useRecoilState(oldPagingState);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (isDataNeedsToBeUpdated) {
      setIsLoading(true);
      setIsDataNeedsToBeUpdated(false);
      getRoutes(filters, sort, paging)
        .then((response) => {
          console.log(response.data);
          const routesArray = response.data.routes;
          if (routesArray == null) {
            setPaging(
              set(
                Object.assign({}, paging),
                'pageNumber',
                Math.max(get(oldPaging, 'pageNumber', 1), 1)
              )
            );
            setIsDataNeedsToBeUpdated(true);
          } else {
            setRoutes(response.data);
          }
        })
        .catch((err) => {
          toast.error(get(err, 'response.data.message', 'Error loading data'));
        })
        .finally(() => setIsLoading(false));
    }
  });

  return (
    <div className="container pt-4">
      <Toaster position="top-center" reverseOrder={false} />
      <div className="card">
        <div className="card-title-2">ROUTES</div>
        <DeleteAllRoutes />
        <CountDistanceGreater />
        <CountAllRoutes />
      </div>
      <div className="card">
        <div className="card-title-1">NAVIGATOR</div>
        <DijkstraForm />
        <AddRoute />
      </div>
      <div className="card mt-4">
        <SortForm />
        <div className="horizontal-placer">
          <FiltersForm />
          <ModalWindow />
          <PagingForm />
          <DeleteRouteButton />
          <ReloadButton isLoading={isLoading} />
        </div>
        <RoutesTable />
      </div>
    </div>
  );
}

export default App;
