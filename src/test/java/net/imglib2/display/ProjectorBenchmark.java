/*
 * #%L
 * ImgLib2: a general-purpose, multidimensional image processing library.
 * %%
 * Copyright (C) 2009 - 2015 Tobias Pietzsch, Stephan Preibisch, Barry DeZonia,
 * Stephan Saalfeld, Curtis Rueden, Albert Cardona, Christian Dietz, Jean-Yves
 * Tinevez, Johannes Schindelin, Jonathan Hale, Lee Kamentsky, Larry Lindsey, Mark
 * Hiner, Michael Zinsmaier, Martin Horn, Grant Harris, Aivar Grislis, John
 * Bogovic, Steffen Jaensch, Stefan Helfrich, Jan Funke, Nick Perry, Mark Longair,
 * Melissa Linkert and Dimiter Prodanov.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package net.imglib2.display;

import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.converter.Converter;
import net.imglib2.converter.RealFloatConverter;
import net.imglib2.display.projector.IterableIntervalProjector2D;
import net.imglib2.display.projector.RandomAccessibleProjector2D;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.interpolation.randomaccess.NearestNeighborInterpolatorFactory;
import net.imglib2.realtransform.AffineTransform3D;
import net.imglib2.realtransform.RealViews;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.util.BenchmarkHelper;
import net.imglib2.view.Views;

public class ProjectorBenchmark
{
	public static < A, B > void benchmarkIterableIntervalProjector2D(
			final RandomAccessibleInterval< A > source,
			final IterableInterval< B > target,
			final Converter< A, B > converter )
	{
		final IterableIntervalProjector2D< A, B > projector =
				new IterableIntervalProjector2D< A, B >( 0, 1, source, target, converter );

		BenchmarkHelper.benchmarkAndPrint( 100, false, new Runnable()
		{
			@Override
			public void run()
			{
				for ( int z = 0; z < source.dimension( 2 ); ++z )
				{
					projector.setPosition( z, 2 );
					projector.map();
				}
			}
		} );
	}

	public static < A, B > void benchmarkRandomAccessibleProjector2D(
			final RandomAccessibleInterval< A > source,
			final RandomAccessibleInterval< B > target,
			final Converter< A, B > converter )
	{
		final RandomAccessibleProjector2D< A, B > projector =
				new RandomAccessibleProjector2D< A, B >( 0, 1, source, target, converter );

		BenchmarkHelper.benchmarkAndPrint( 100, false, new Runnable()
		{
			@Override
			public void run()
			{
				for ( int z = 0; z < source.dimension( 2 ); ++z )
				{
					projector.setPosition( z, 2 );
					projector.map();
				}
			}
		} );
	}

//	public static < A, B > void benchmarkXYRandomAccessibleProjector(
//			final RandomAccessibleInterval< A > source,
//			final RandomAccessibleInterval< B > target,
//			final Converter< A, B > converter )
//	{
//		final XYRandomAccessibleProjector< A, B > projector =
//				new XYRandomAccessibleProjector< A, B >( source, target, converter );
//
//		BenchmarkHelper.benchmarkAndPrint( 100, false, new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				for ( int z = 0; z < source.dimension( 2 ); ++z )
//				{
//					projector.setPosition( z, 2 );
//					projector.map();
//				}
//			}
//		} );
//	}

	public static void main( final String[] args )
	{
		final Img< FloatType > source = ArrayImgs.floats( 1000, 1000, 10 );
		final Img< FloatType > target = ArrayImgs.floats( 1000, 1000 );
		final Converter< FloatType, FloatType > converter = new RealFloatConverter< FloatType >();

		final AffineTransform3D t = new AffineTransform3D();
		final RandomAccessibleInterval< FloatType > view = Views.interval(
				RealViews.affine(
						Views.interpolate( source, new NearestNeighborInterpolatorFactory< FloatType >() ),
						t ),
				source );

//		System.out.println( "IterableIntervalProjector2D Img" );
//		benchmarkIterableIntervalProjector2D( source, target, converter );

//		System.out.println( "IterableIntervalProjector2D Affine" );
//		benchmarkIterableIntervalProjector2D( view, target, converter );

//		System.out.println( "RandomAccessibleProjector2D Img" );
//		benchmarkRandomAccessibleProjector2D( source, target, converter );

//		System.out.println( "RandomAccessibleProjector2D Affine" );
//		benchmarkRandomAccessibleProjector2D( view, target, converter );

//		System.out.println( "XYRandomAccessibleProjector Img" );
//		benchmarkXYRandomAccessibleProjector( source, target, converter );

//		System.out.println( "XYRandomAccessibleProjector Affine" );
//		benchmarkXYRandomAccessibleProjector( view, target, converter );

		for ( int i = 0; i < 10; ++i )
		{
			System.out.println( "IterableIntervalProjector2D Img" );
			benchmarkIterableIntervalProjector2D( source, target, converter );
			System.out.println( "IterableIntervalProjector2D Affine" );
			benchmarkIterableIntervalProjector2D( view, target, converter );

//			System.out.println( "RandomAccessibleProjector2D Img" );
//			benchmarkRandomAccessibleProjector2D( source, target, converter );
//			System.out.println( "RandomAccessibleProjector2D Affine" );
//			benchmarkRandomAccessibleProjector2D( view, target, converter );

//			System.out.println( "XYRandomAccessibleProjector Img" );
//			benchmarkXYRandomAccessibleProjector( source, target, converter );
//			System.out.println( "XYRandomAccessibleProjector Affine" );
//			benchmarkXYRandomAccessibleProjector( view, target, converter );

			System.out.println( "--------------------------------" );
		}
	}
}
