/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * @file           : main.c
  * @brief          : Main program body
  ******************************************************************************
  * @attention
  *
  * Copyright (c) 2022 STMicroelectronics.
  * All rights reserved.
  *
  * This software is licensed under terms that can be found in the LICENSE file
  * in the root directory of this software component.
  * If no LICENSE file comes with this software, it is provided AS-IS.
  *
  ******************************************************************************
  */
/* USER CODE END Header */
/* Includes ------------------------------------------------------------------*/
#include "main.h"
#include "gpio.h"

/* Private includes ----------------------------------------------------------*/
/* USER CODE BEGIN Includes */

/* USER CODE END Includes */

/* Private typedef -----------------------------------------------------------*/
/* USER CODE BEGIN PTD */

/* USER CODE END PTD */

/* Private define ------------------------------------------------------------*/
/* USER CODE BEGIN PD */
/* USER CODE END PD */

/* Private macro -------------------------------------------------------------*/
/* USER CODE BEGIN PM */

/* USER CODE END PM */

/* Private variables ---------------------------------------------------------*/

/* USER CODE BEGIN PV */

uint16_t GREEN_LIGHT = GPIO_PIN_13;
uint16_t YELLOW_LIGHT = GPIO_PIN_14;
uint16_t RED_LIGHT = GPIO_PIN_15;

uint16_t BUTTON = GPIO_PIN_15;
/* USER CODE END PV */

/* Private function prototypes -----------------------------------------------*/
void SystemClock_Config(void);
/* USER CODE BEGIN PFP */

/* USER CODE END PFP */

/* Private user code ---------------------------------------------------------*/
/* USER CODE BEGIN 0 */

void wait(uint32_t duration)
{
	uint32_t begin = HAL_GetTick();
	while((HAL_GetTick() - begin) < duration){}
}

void turnSpecificLightOff(uint16_t light_type)
{
	HAL_GPIO_WritePin(GPIOD, light_type, 0);
}

void shutdownAll()
{
	turnSpecificLightOff(GREEN_LIGHT);
	turnSpecificLightOff(YELLOW_LIGHT);
	turnSpecificLightOff(RED_LIGHT);

}

void turnSpecificLightOn(uint16_t light_type)
{
	HAL_GPIO_WritePin(GPIOD, light_type, 1);
}

void blinkLight(uint32_t count, uint16_t light_type, uint32_t duration)
{
	for(uint32_t i = 0; i < count; i++)
	{
		wait(duration);
		turnSpecificLightOn(light_type);
		wait(duration);
		turnSpecificLightOff(light_type);
	}
}

/* USER CODE END 0 */

/**
  * @brief  The application entry point.
  * @retval int
  */
int main(void)
{
  /* USER CODE BEGIN 1 */

  /* USER CODE END 1 */

  /* MCU Configuration--------------------------------------------------------*/

  /* Reset of all peripherals, Initializes the Flash interface and the Systick. */
  HAL_Init();

  /* USER CODE BEGIN Init */
  /* USER CODE END Init */

  /* Configure the system clock */
  SystemClock_Config();

  /* USER CODE BEGIN SysInit */
  /* USER CODE END SysInit */

  /* Initialize all configured peripherals */

  MX_GPIO_Init();

  /* USER CODE BEGIN 2 */

  uint32_t startTime = 0;
  uint32_t greenLightDuration = 10000;
  uint32_t blinkDuration = 500;
  uint32_t redLightDuration = 4 * greenLightDuration;
  uint32_t yellowLightDuration = 3000;
  uint8_t buttonFlag = 0;

  /* USER CODE END 2 */

  /* Infinite loop */
  /* USER CODE BEGIN WHILE */
  while (1)
  {
	  startTime = HAL_GetTick();

	  turnSpecificLightOn(RED_LIGHT);

	  while((HAL_GetTick() - startTime) < redLightDuration)
	  {
		  if(HAL_GPIO_ReadPin(GPIOC, BUTTON) == 0 && buttonFlag == 0) {
			  redLightDuration = redLightDuration / 4;
			  buttonFlag = 1;
		  }
	  }

	  turnSpecificLightOff(RED_LIGHT);
	  turnSpecificLightOn(GREEN_LIGHT);

	  wait(greenLightDuration);

	  blinkLight(3, GREEN_LIGHT, blinkDuration);

	  redLightDuration = 4 * greenLightDuration;
	  buttonFlag = 0;

	  turnSpecificLightOn(YELLOW_LIGHT);

	  startTime = HAL_GetTick();
	  while((HAL_GetTick() - startTime) < yellowLightDuration)
	  {
		  if(HAL_GPIO_ReadPin(GPIOC, BUTTON) == 0 && buttonFlag == 0) {
			  redLightDuration = redLightDuration / 4;
			  buttonFlag = 1;
		  }
	  }

    /* USER CODE END WHILE */

    /* USER CODE BEGIN 3 */
  }
  /* USER CODE END 3 */
}

/**
  * @brief System Clock Configuration
  * @retval None
  */
void SystemClock_Config(void)
{
  RCC_OscInitTypeDef RCC_OscInitStruct = {0};
  RCC_ClkInitTypeDef RCC_ClkInitStruct = {0};

  /** Configure the main internal regulator output voltage
  */
  __HAL_RCC_PWR_CLK_ENABLE();
  __HAL_PWR_VOLTAGESCALING_CONFIG(PWR_REGULATOR_VOLTAGE_SCALE3);

  /** Initializes the RCC Oscillators according to the specified parameters
  * in the RCC_OscInitTypeDef structure.
  */
  RCC_OscInitStruct.OscillatorType = RCC_OSCILLATORTYPE_HSI;
  RCC_OscInitStruct.HSIState = RCC_HSI_ON;
  RCC_OscInitStruct.HSICalibrationValue = RCC_HSICALIBRATION_DEFAULT;
  RCC_OscInitStruct.PLL.PLLState = RCC_PLL_NONE;
  if (HAL_RCC_OscConfig(&RCC_OscInitStruct) != HAL_OK)
  {
    Error_Handler();
  }

  /** Initializes the CPU, AHB and APB buses clocks
  */
  RCC_ClkInitStruct.ClockType = RCC_CLOCKTYPE_HCLK|RCC_CLOCKTYPE_SYSCLK
                              |RCC_CLOCKTYPE_PCLK1|RCC_CLOCKTYPE_PCLK2;
  RCC_ClkInitStruct.SYSCLKSource = RCC_SYSCLKSOURCE_HSI;
  RCC_ClkInitStruct.AHBCLKDivider = RCC_SYSCLK_DIV1;
  RCC_ClkInitStruct.APB1CLKDivider = RCC_HCLK_DIV1;
  RCC_ClkInitStruct.APB2CLKDivider = RCC_HCLK_DIV1;

  if (HAL_RCC_ClockConfig(&RCC_ClkInitStruct, FLASH_LATENCY_0) != HAL_OK)
  {
    Error_Handler();
  }
}

/* USER CODE BEGIN 4 */

/* USER CODE END 4 */

/**
  * @brief  This function is executed in case of error occurrence.
  * @retval None
  */
void Error_Handler(void)
{
  /* USER CODE BEGIN Error_Handler_Debug */
  /* User can add his own implementation to report the HAL error return state */
  __disable_irq();
  while (1)
  {
  }
  /* USER CODE END Error_Handler_Debug */
}

#ifdef  USE_FULL_ASSERT
/**
  * @brief  Reports the name of the source file and the source line number
  *         where the assert_param error has occurred.
  * @param  file: pointer to the source file name
  * @param  line: assert_param error line source number
  * @retval None
  */
void assert_failed(uint8_t *file, uint32_t line)
{
  /* USER CODE BEGIN 6 */
  /* User can add his own implementation to report the file name and line number,
     ex: printf("Wrong parameters value: file %s on line %d\r\n", file, line) */
  /* USER CODE END 6 */
}
#endif /* USE_FULL_ASSERT */
