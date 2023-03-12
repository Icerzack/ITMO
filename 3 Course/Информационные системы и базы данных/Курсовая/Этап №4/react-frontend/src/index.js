import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import ErrorPage from "./pages/ErrorPage";
import Profile from "./pages/Profile/Profile";
import Baa from "./pages/Baa/Baa";
import BaaView from "./pages/Baa/BaaView";
import Login from "./pages/Login-Register/Login";
import Register from "./pages/Login-Register/Register";
import Trainings from "./pages/Trainings/Trainings";
import TrainingView from "./pages/Trainings/TrainingView";
import Preparations from "./pages/Preparations/Preparations";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App></App>,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "/login",
        element: <Login></Login>,
      },
      {
        path: "/register",
        element: <Register></Register>,
      },
      {
        path: "/sportsman/baa",
        element: <Baa></Baa>,
      },
      {
        path: "/sportsman/baa/:id",
        element: <BaaView></BaaView>,
      },
      {
        path: "/sportsman/:id",
        element: <Profile></Profile>,
      },
      {
        path: "/sportsman/trainings",
        element: <Trainings></Trainings>,
      },
      {
        path: "/sportsman/trainings/:id",
        element: <TrainingView></TrainingView>,
      },
      {
        path: "/sportsman/preparations",
        element: <Preparations></Preparations>,
      },
    ],
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
