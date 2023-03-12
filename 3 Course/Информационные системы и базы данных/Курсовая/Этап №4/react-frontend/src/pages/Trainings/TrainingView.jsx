import * as React from "react";
import Box from "@mui/material/Box";
import { useParams } from "react-router-dom";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import DisplayCard from "../../components/card";
import Button from "@mui/material/Button";

export default function TrainingView(props) {
  const { id } = useParams();

  const [name, setName] = React.useState("");
  const [description, setDescription] = React.useState("");
  const [exercises, setExercises] = React.useState([]);
  const [uses, setUses] = React.useState("");
  const [allTimeRatingDifference, setAllTimeRatingDifference] =
    React.useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const url = "http://localhost:32456/get/training/" + id;
    axios({
      method: "get",
      url: url,
    })
      .then(function (response) {
        let temp = response.data.body;
        setName(temp[0].name);
        setDescription(temp[0].description);
        setUses(temp[0].uses);
        setAllTimeRatingDifference(temp[0].allTimeRatingDifference);
      })
      .catch(function (error) {
        if (error.response) {
          navigate("/sportsman/trainings");
        }
      });
  }, []);

  let h = window.innerHeight;
  let gridHeight = h - 60;

const handleButtonClick = () => {
  const url =
  "http://localhost:32456/get/training/exercises?training_id=" + id;
axios({
  method: "get",
  url: url,
}).then(function (response) {
  setExercises(response.data.body);
});
}

  const handleCardClick = () => {};

  return (
    <Box
      sx={{
        height: gridHeight,
      }}
    >
      <Box
        sx={{
          height: "100%",
          display: "flex",
          justifyContent: "center",
          alignItems: "left",
          flexDirection: "column",
          ml: "50px",
          mt: "80px",
        }}
      >
        <img
          src="https://media.istockphoto.com/id/1367321305/photo/preparation-is-the-key.jpg?b=1&s=170667a&w=0&k=20&c=5wEDSTlFt-oiR_oCPxKqeMyloe7-oUZjGYDTWV88Xj0="
          width="200"
          height="300"
          alt="lorem"
        />
        <Box>
          <h1>{name}</h1>
        </Box>
        <Button
                variant="contained"
                sx={{ mb: 5 }}
                onClick={handleButtonClick}
              >
                Показать Упражнения
              </Button>
        <Box sx={{ width: "100%", overflow: "auto" }}>
          {exercises.map((element) => (
            <DisplayCard
              key={element.id}
              name={element.id}
              title={element.name}
              onCardClick={handleCardClick}
              shortDescription={element.description}
            ></DisplayCard>
          ))}
        </Box>
        <Box>
          <h3>Количество использований: {uses}</h3>
          <h3>Рейтинг: {allTimeRatingDifference}</h3>
        </Box>
      </Box>
    </Box>
  );
}
