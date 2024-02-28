import { atom } from 'recoil';

export const isDataNeedsToBeUpdatedState = atom({
  key: 'isDataNeedsToBeUpdatedState',
  default: true
});

export const sortState = atom({
  key: 'sortState',
  default: []
});

export const filtersState = atom({
  key: 'filtersState',
  default: {}
});

export const pagingState = atom({
  key: 'pagingState',
  default: {}
});

export const oldPagingState = atom({
  key: 'oldPagingState',
  default: {}
});

export const selectedRoutesId = atom({
  key: 'selectedRoutesId',
  default: []
});

export const feedbackRouteValidator = atom({
  key: 'feedbackRouteValidator',
  default: {}
});

export const wasValidated = atom({
  key: 'wasValidated',
  default: false
});

export const isAddingWithLocationIds = atom({
  key: 'isAddingWithLocationIds',
  default: false
});

export const isEditingRoute = atom({
  key: 'isEditingRoute',
  default: false
});

export const bufferRoute = atom({
  key: 'bufferRoute',
  default: {}
});

export const showModalForm = atom({
  key: 'showModalForm',
  default: false
});

export const routesState = atom({
  key: 'routesState',
  default: []
});
