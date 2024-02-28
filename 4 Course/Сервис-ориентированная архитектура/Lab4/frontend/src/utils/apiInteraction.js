import axios from 'axios';
import get from 'lodash.get';

axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*'; // for all requests
axios.defaults.headers.common['Access-Control-Allow-Method'] = '*'; // for all requests
axios.defaults.headers.common['Access-Control-Allow-Headers'] = '*'; // for all requests
axios.defaults.headers.common['Access-Control-Allow-Credentials'] = 'true'; // for all requests

const SERVICE_1 = 'https://localhost:8081/api';
const SERVICE_2 = 'https://localhost:8089/navigator/api/v1';

const filtersToStr = (filters) => {
  let queryString = '';

  for (const key in filters) {
    const value = filters[key];

    for (const op in value) {
      const opValue = value[op];

      let operator = '';
      switch (op) {
        case 'eq':
          operator = '=';
          break;
        case 'neq':
          operator = '!=';
          break;
        case 'gt':
          operator = '>';
          break;
        case 'lt':
          operator = '<';
          break;
        case 'get':
          operator = '>=';
          break;
        case 'let':
          operator = '<=';
          break;
        default:
          break;
      }

      if (operator) {
        if (queryString !== '') {
          queryString += '&';
        }
        queryString += `filter=${key}${operator}${opValue}`;
      }
    }
  }

  return queryString;
};

const sortToStr = (sort) => {
  let queryString = '';

  for (const key in sort) {
    if (queryString !== '') {
      queryString += '&';
    }
    console.log(sort[key]);
    queryString += `sort=${sort[key]}`;
  }

  return queryString;
};

const pagingToStr = (paging) => {
  const limit = get(paging, 'elementsCount', 5);
  const pageNumber = get(paging, 'pageNumber', 1);
  return (
    (limit !== 5 ? 'elementsCount=' + limit : '') +
    (limit !== 5 && pageNumber !== 1 ? '&' : '') +
    (pageNumber !== 1 ? 'page=' + pageNumber : '')
  );
};

export const getRoutes = (filters, sort, paging) => {
  const options = [filtersToStr(filters), sortToStr(sort), pagingToStr(paging)]
    .filter((s) => s.length)
    .join('&');
  return axios.get(SERVICE_1 + '/routes?' + options);
};

export const deleteRoute = (id) => {
  return axios.delete(SERVICE_1 + '/routes/' + id);
};

export const findShortest = (fromId, toId) => {
  return axios.get(SERVICE_2 + '/route/' + fromId + '/' + toId + '/shortest');
};

export const addRouteS2 = (fromId, toId) => {
  return axios.post(SERVICE_2 + '/route/add/' + fromId + '/' + toId);
};

export const deleteAllRoutesS1 = (distance) => {
  return axios.delete(SERVICE_1 + '/routes/distances/' + distance);
};

export const countAllRoutesS1 = () => {
  return axios.get(SERVICE_1 + '/routes/distances/sum');
};

export const countAllRoutesDistanceGreaterS1 = (distance) => {
  return axios.get(SERVICE_1 + '/routes/distances/' + distance + '/count/greater');
};

const washRoute = (route) => {
  const washedRoute = {
    name: route.name,
    from: {
      coordinates: {
        x: parseInt(route.from.coordinates.x),
        y: parseFloat(route.from.coordinates.y)
      },
      name: route.from.name
    },
    to: {
      coordinates: {
        x: parseInt(route.to.coordinates.x),
        y: parseFloat(route.to.coordinates.y)
      },
      name: route.to.name
    },
    distance: parseFloat(route.distance)
  };

  return washedRoute;
};

export const postRoute = (route) => {
  return axios.post(SERVICE_1 + '/routes', JSON.stringify(washRoute(route)), {
    headers: { 'Content-Type': 'application/json' }
  });
};

export const putRoute = (route) => {
  return axios.put(SERVICE_1 + '/routes/' + route.id, JSON.stringify(washRoute(route)), {
    headers: { 'Content-Type': 'application/json' }
  });
};
