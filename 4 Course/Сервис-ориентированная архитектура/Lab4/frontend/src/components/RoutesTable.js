import { useRecoilState, useSetRecoilState } from 'recoil';
import {
  bufferRoute,
  isAddingWithLocationIds,
  isDataNeedsToBeUpdatedState,
  isEditingRoute,
  oldPagingState,
  pagingState,
  routesState,
  selectedRoutesId,
  showModalForm,
  wasValidated
} from '../state/atoms';
import { fieldList } from '../utils/constants';
import get from 'lodash.get';
import set from 'lodash.set';
import Button from 'react-bootstrap/Button';
import { ButtonGroup, ToggleButton } from 'react-bootstrap';

export const RoutesTable = () => {
  const setIsDataNeedsToBeUpdated = useSetRecoilState(isDataNeedsToBeUpdatedState);
  const setShow = useSetRecoilState(showModalForm);
  const setIsEditing = useSetRecoilState(isEditingRoute);
  const setBufferRoute = useSetRecoilState(bufferRoute);
  const setValidated = useSetRecoilState(wasValidated);
  const setIsAddingWithLocationIds = useSetRecoilState(isAddingWithLocationIds);
  const [selectedIds, setSelectedId] = useRecoilState(selectedRoutesId);
  const [paging, setPaging] = useRecoilState(pagingState);
  const setOldPaging = useSetRecoilState(oldPagingState);

  const [routes] = useRecoilState(routesState);

  const edit = (route) => {
    setBufferRoute(route);
    setValidated(false);
    setIsAddingWithLocationIds(false);
    setIsEditing(true);
    setShow(true);
  };

  const select = (event, routeId) => {
    if (selectedIds.indexOf(routeId) >= 0) {
      setSelectedId(
        selectedIds.filter((x) => {
          return x !== routeId;
        })
      );
    } else {
      setSelectedId([...selectedIds, routeId]);
    }
    console.log(selectedIds);
  };

  return (
    <>
      <table className="table table-bordered mt-3">
        <thead className="text-center align-middle">
          <tr>
            <th rowSpan="2">ID</th>
            <th rowSpan="2">Name</th>
            <th rowSpan="2">Creation Date</th>
            <th colSpan="4">From Location</th>
            <th colSpan="4">To Location</th>
            <th rowSpan="2">Distance</th>
          </tr>
          <tr>
            <th>id</th>
            <th>x</th>
            <th>y</th>
            <th>name</th>
            <th>id</th>
            <th>x</th>
            <th>y</th>
            <th>name</th>
          </tr>
        </thead>
        <tbody>
          {routes?.routes?.map((route) => (
            <tr
              key={route.id}
              data-bs-toggle="tooltip"
              title={JSON.stringify(route, undefined, 4)}
              className={selectedIds.indexOf(route.id) !== -1 ? 'selected' : ''}
              onDoubleClick={() => {
                edit(route);
              }}
              onClick={(event) => {
                select(event, route.id);
              }}>
              {fieldList.map((field) => (
                <td key={field}>{get(route, field.replaceAll('_', '.'), '')}</td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
      <div className="text-center">
        <ButtonGroup>
          <Button
            variant="dark"
            onClick={() => {
              setOldPaging(
                set(Object.assign({}, paging), 'pageNumber', get(paging, 'pageNumber', 1))
              );
              setPaging(
                set(
                  Object.assign({}, paging),
                  'pageNumber',
                  Math.max(get(paging, 'pageNumber', 1) - 1, 1)
                )
              );
              setIsDataNeedsToBeUpdated(true);
            }}>
            &lt;
          </Button>
          <ToggleButton variant="dark" value="page">
            {get(paging, 'pageNumber', 1)}
          </ToggleButton>
          <Button
            variant="dark"
            onClick={() => {
              setOldPaging(
                set(Object.assign({}, paging), 'pageNumber', get(paging, 'pageNumber', 1))
              );
              setPaging(
                set(Object.assign({}, paging), 'pageNumber', get(paging, 'pageNumber', 1) + 1)
              );
              setIsDataNeedsToBeUpdated(true);
            }}>
            &gt;
          </Button>
        </ButtonGroup>
      </div>
    </>
  );
};
