/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;

/**
 * Allows for custom modification of an application context's bean definitions,
 * adapting the bean property values of the context's underlying bean factory.
 *
 * <p>Application contexts can auto-detect BeanFactoryPostProcessor beans in
 * their bean definitions and apply them before any other beans get created.
 *
 * <p>Useful for custom config files targeted at system administrators that
 * override bean properties configured in the application context.
 *
 * <p>See PropertyResourceConfigurer and its concrete implementations
 * for out-of-the-box solutions that address such configuration needs.
 *
 * <p>A BeanFactoryPostProcessor may interact with and modify bean
 * definitions, but never bean instances. Doing so may cause premature bean
 * instantiation, violating the container and causing unintended side-effects.
 * If bean instance interaction is required, consider implementing
 * {@link BeanPostProcessor} instead.
 *
 * @author Juergen Hoeller
 * @since 06.07.2003
 * @see BeanPostProcessor
 * @see PropertyResourceConfigurer
 */
//对于BeanPostProcessor的处理与BeanFactoryPostProcessor的处理极为相似，但是似乎又有些不一样的地方。经过反复的对比发现，对于BeanFactoryPostProcessor的处理要区分两种情况，
//		一种方式是通过硬编码方式的处理，另一种是通过配置文件方式的处理。那么为什么在BeanPostProcessor的处理中只考虑了配置文件的方式而不考虑硬编码的方式呢？对于BeanFactoryPostProcessor的处理，
//		不但要实现注册功能，而且还要实现对后处理器的激活操作，所以需要载入配置中的定义，并进行激活；而对于BeanPostProcessor并不需要马上调用，
//		再说，硬编码的方式实现的功能是将后处理器提取并调用，这里并不需要调用，当然不需要考虑硬编码的方式了，这里的功能只需要将配置文件的BeanPostProcessor提取出来并注册进入beanFactory就可以了。
public interface BeanFactoryPostProcessor {

	/**
	 * Modify the application context's internal bean factory after its standard
	 * initialization. All bean definitions will have been loaded, but no beans
	 * will have been instantiated yet. This allows for overriding or adding
	 * properties even to eager-initializing beans.
	 * @param beanFactory the bean factory used by the application context
	 * @throws org.springframework.beans.BeansException in case of errors
	 */
	//这个方法会被Spring在容器初始化过程中调用，调用时机是所有bean的定义信息都已经初始化好，
	// 但是这些bean还没有实例化。
	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
	//可以在Bean被创建之前，获取容器中的Bean的定义信息，并且可以进行修改。实现类中的postProcessBeanFactory方法只会
	//被执行一次，且先于BeanPostProcesor接口的方法。

//	4. Spring容器的扩展点
//	前面两节简单介绍了扩展Spring的两种方式：基于XML和基于Java的配置。通过这两种方式，我们可以在运行时收集到用户的配置信息，同时向Spring注册实际处理这些配置信息的Bean。
//
//	但这些注册进去的Bean实际上是如何工作的呢？我们通过什么方式能使我们的程序逻辑和Spring的容器紧密合作并无缝插入到用户bean的生命周期中呢？
//
//	这里简单介绍Spring容器最常用的两个扩展点： BeanFactoryPostProcessor 和
// BeanFactoryPostProcessor 提供了一个方法： postProcessBeanFactory 。
//
//这个方法会被Spring在容器初始化过程中调用，调用时机是所有bean的定义信息都已经初始化好，但是这些bean还没有实例化。。
}
